package laravel.echo

import android.util.Log
import laravel.echo.channel.Channel
import laravel.echo.channel.PresenceChannel
import laravel.echo.connector.Connector
import laravel.echo.connector.SocketIoConnector
import org.json.JSONObject

/**
 * Created by baslenvm on 19/8/2560.
 */
class Echo(private var options: Options) {

    private var connector:Connector?  = null

    init {
        Log.i("Echo/Status", "Start")
        Log.i("Echo/Broadcaster", this.options.broadcaster)
        when (this.options.broadcaster){
            "socket.io" -> this.connector = SocketIoConnector(this.options)
            "pusher" -> Log.e("Echo/Status", "Broadcaster is coming")
        }
    }

    fun listen(channel: String, event: String, callback: (data: JSONObject) -> Unit):Channel {
        return this.connector!!.listen(channel, event, callback)
    }

    fun channel(channel: String):Channel {
        return this.connector!!.channel(channel)
    }

    fun privat(channel: String):Channel {
        return this.connector!!.privateChannel(channel)
    }

    fun join(channel: String):PresenceChannel {
        return this.connector!!.presenceChannel(channel)
    }

    fun leave(channel: String) {
        this.connector!!.leave(channel)
    }

    fun socketId():String {
        return this.connector!!.socketId()
    }

    fun disconnect(){
        this.connector!!.disconnect()
    }
}