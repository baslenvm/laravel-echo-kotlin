package laravel.echo.connector


/**
 * Created by baslenvm on 20/8/2560.
 */
import laravel.echo.Options
import laravel.echo.channel.Channel
import laravel.echo.channel.PresenceChannel
import org.json.JSONObject

abstract class Connector (val options:Options) {
    init {
        this.connect()
    }
    abstract fun connect()
    abstract fun channel(channel: String): Channel
    abstract fun listen(name: String, event: String, callback: (data: JSONObject) -> Unit): Channel
    abstract fun privateChannel(channel: String): Channel
    abstract fun presenceChannel(channel: String): PresenceChannel
    abstract fun leave(channel: String)
    abstract fun socketId(): String
    abstract fun disconnect()
}