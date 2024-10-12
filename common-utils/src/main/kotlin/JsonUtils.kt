
import arrow.core.raise.catch
import arrow.core.raise.either
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.util.LinkedMultiValueMap

object JsonUtils {
    private var mapper: ObjectMapper = jacksonObjectMapper().apply {
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    }

    fun Any.toMultiValueMap() = either {
        catch({
            mapper.convertValue<Map<String, String>>(this@toMultiValueMap).let {
                LinkedMultiValueMap<String, String>().apply { setAll(it) }
            }
        }) {
            getLogger().error("[JsonUtils][toMultiValueMap] Object to multiValueMap convert error: ${it.message}")
            raise(JsonUtilsError.ParseFail("JsonUtils toMultiValueMap Error"))
        }
    }
}

sealed class JsonUtilsError {
    abstract val message: String
    data class ParseFail(override val message: String) : JsonUtilsError()
}
