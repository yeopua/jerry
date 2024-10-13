
import arrow.core.raise.catch
import arrow.core.raise.either
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.util.LinkedMultiValueMap

object JsonUtils {
    private var mapper: ObjectMapper = jacksonObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private var snakeCaseMapper: ObjectMapper = jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    fun Any?.toMultiValueMap() = either {
        catch({
            this@toMultiValueMap?.let {
                mapper.convertValue<Map<String, String>>(it).let {
                    LinkedMultiValueMap<String, String>().apply { setAll(it) }
                }
            }
        }) {
            getLogger().error("[JsonUtils][toMultiValueMap] Object to multiValueMap convert error: ${it.message}")
            raise(JsonUtilsError.ParseFail("JsonUtils toMultiValueMap Error"))
        }
    }

    fun Any?.toSnakeCaseMultiValueMap() = either {
        catch({
            this@toSnakeCaseMultiValueMap?.let {
                snakeCaseMapper
                    .convertValue<Map<String, String>>(it).let {
                        LinkedMultiValueMap<String, String>().apply { setAll(it) }
                    }
            }
        }) {
            getLogger().error("[JsonUtils][toSnakeCaseMultiValueMap] Object to multiValueMap convert error : ${it.message}", it)
            raise(JsonUtilsError.ParseFail("JsonUtils toSnakeCaseMultiValueMap Error"))
        }
    }
}

sealed class JsonUtilsError {
    abstract val message: String
    data class ParseFail(override val message: String) : JsonUtilsError()
}
