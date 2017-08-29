package laravel.echo.connector

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import laravel.echo.Options
import laravel.echo.channel.Channel
import laravel.echo.channel.PresenceChannel
import laravel.echo.channel.SocketIoChannel
import org.json.JSONObject


/**
 * Created by baslenvm on 20/8/2560.
 */
class SocketIoConnector(options: Options):Connector(options) {


    var socket:Socket? = null
    var channels:MutableMap<String, SocketIoChannel> = mutableMapOf()

    override fun connect() {
        Log.i("Echo/Status", "Connect")
        this.socket = IO.socket(this.options.host)
        socket!!.connect()
    }

    override fun listen(name: String, event: String, callback: (data:JSONObject) -> Unit): SocketIoChannel {
        return this.channel(name).listen(event, callback)
    }

    override fun channel(name: String): SocketIoChannel {
        this.channels[name] = SocketIoChannel(this.socket!!, name, this.options)

        return this.channels[name]!!
    }
    override fun privateChannel(channel: String): Channel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun presenceChannel(channel: String): PresenceChannel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun leave(channel: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun socketId(): String {
        return this.socket!!.id()
    }

    override fun disconnect() {
        this.socket!!.disconnect()
        this.socket = null
        this.channels = mutableMapOf()
    }
}