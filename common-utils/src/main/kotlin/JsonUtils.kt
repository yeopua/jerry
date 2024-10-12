import arrow.core.raise.catch
import arrow.core.raise.either
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.util.LinkedMultiValueMap

object JsonUtils {
    private var mapper: ObjectMapper = jacksonObjectMapper()
    fun Any.toMultiValueMap() = either {
        catch({
            mapper.convertValue<Map<String, String>>(this).let {
                LinkedMultiValueMap<String, String>().apply { setAll(it) }
            }
        }) {
            getLogger().error("[JsonUtils][toMultiValueMap] Object to multiValueMap convert error: ${it.message}")
            raise(JsonUtilsError.ParseFail)
        }
    }
}

sealed interface JsonUtilsError {
    data object ParseFail : JsonUtilsError
}
