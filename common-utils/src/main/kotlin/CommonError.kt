sealed class CommonError {
    abstract val message: String

    data class ConversionError(override val message: String, val source: Any? = null) : CommonError()
    data class DataSourceError(override val message: String, val source: Any? = null) : CommonError()
}
