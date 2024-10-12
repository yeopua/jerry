import arrow.core.Either
import arrow.core.left
import arrow.core.right

@JvmInline
value class NonEmptyString private constructor(private val s: String) {
    override fun toString(): String = s

    companion object {
        fun from(s: String): Either<CommonError.ConversionError, NonEmptyString> {
            return if (s.isBlank()) {
                CommonError.ConversionError("NonEmptyString Convert Fail", s).left()
            } else {
                NonEmptyString(s).right()
            }
        }
    }
}

fun String.toNonEmptyString(): Either<CommonError.ConversionError, NonEmptyString> = NonEmptyString.from(this)
