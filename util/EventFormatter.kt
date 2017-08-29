package laravel.echo.util

/**
 * Created by baslenvm on 19/8/2560.
 */
class EventFormatter(namespace:String) {


    /**
     * Create a new class instance.
     *
     * @params  {string | boolean} namespace
     */
    init {
        this.setNamespace(namespace);
    }

    private var namespace: String? = null

    /**
     * Set the event namespace.
     *
     * @param  {string} value
     * @return {void}
     */
    fun setNamespace(value: String) {
        this.namespace = value
    }

    /**
     * Format the given event name.
     *
     * @param  {string} event
     * @return {string}
     */
    fun format(event: String): String {
        var ev = event
        if (ev.toCharArray()[0] == '.' || ev.toCharArray()[0] == '\\') {
            return ev.substring(1)
        } else if (!this.namespace.isNullOrEmpty()) {
            ev = this.namespace + '.' + ev
        }
        return ev.replace(".", "\\")
//        return event.replace(/\./g, '\\');
    }
}