package com.jerry.common

import com.jerry.duplication.repository.FindDuplicationRunningPolicyRepository
import com.jerry.duplication.repository.SaveDuplicationRunningPolicyRepository
import getLogger
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

annotation class CheckRunning(
    val policy: DuplicationRunningPolicy
)

@Order(-1)
@Aspect
@Component
class CheckRunningAspect(
    private val saveDuplicationRunningPolicyRepository: SaveDuplicationRunningPolicyRepository,
    private val findDuplicationRunningPolicyRepository: FindDuplicationRunningPolicyRepository
) {
    @Around(value = "@annotation(CheckRunning)")
    suspend fun checkDuplicationExecution(joinPoint: ProceedingJoinPoint): Any = mono {
        val checkRunning = (joinPoint.signature as MethodSignature).method.getAnnotation(
            CheckRunning::class.java
        )

        when (isRunning(checkRunning.policy)) {
            true -> throw RuntimeException("이미 실행중입니다.")
            false -> run(checkRunning.policy)
        }
    }
        .then(joinPoint.proceed() as Mono<*>)
        .awaitSingle()

    private suspend fun isRunning(policy: DuplicationRunningPolicy): Boolean {
        val isRunning = findDuplicationRunningPolicyRepository.invoke(policy.redisKey).isRight { !it.toString().isNullOrBlank() }
        if (isRunning) getLogger().info("[checkDuplicationExecution] isRunning!")
        return isRunning
    }

    private suspend fun run(policy: DuplicationRunningPolicy) {
        saveDuplicationRunningPolicyRepository.invoke(policy.redisKey, policy.ttl)
    }
}
