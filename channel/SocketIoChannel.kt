package laravel.echo.channel

/**
 * Created by baslenvm on 20/8/2560.
 */
import android.annotation.SuppressLint
import android.util.Log
import laravel.echo.Options
import io.socket.client.Socket
import laravel.echo.util.EventFormatter
import org.json.JSONObject
import io.socket.emitter.Emitter



class SocketIoChannel(private val socket: Socket,var name: String, override val options: Options) : Channel() {

    private var eventFormatter: EventFormatter? = null
    private var events:MutableMap<String, MutableList<Emitter.Listener>> = mutableMapOf()

    init {
        this.eventFormatter = EventFormatter(this.options.namespace)
        this.subscribe()
        this.configureReconnector()
    }


    fun subscribe(){
        val ms = JSONObject()
        val auth = JSONObject()
        val headers = JSONObject()
        ms.put("channel", this.name)
        Log.i("Echo/Channel", this.name)
        if(this.options.auth.headers.Authorization != "") {
            Log.i("Echo/Status", "Auth : " + this.options.auth.headers.Authorization)
            headers.put("Authorization", this.options.auth.headers.Authorization)
            auth.put("headers", headers)
        }
            ms.put("auth", auth)
        Log.i("Echo/Subscribe", this.name)
        this.socket.emit("subscribe", ms)
    }
    fun unsubscribe() {
        val ms = JSONObject()
        val auth = JSONObject()
        val headers = JSONObject()
        ms.put("channel", this.name)
        headers.put("Authorization", this.options.auth.headers.Authorization)
        auth.put("headers", headers)
        ms.put("auth", auth)
        this.unbind()
        this.socket.emit("unsubscribe", ms)
    }

    override fun listen(event: String, callback: (data:JSONObject) -> Unit): SocketIoChannel {
        Log.i("Echo/listen", event)
        Log.i("Echo/Formatter", this.eventFormatter!!.format(event))
        this.on(this.eventFormatter!!.format(event), callback)
        return this
    }

    fun on(event: String, callback: (data:JSONObject) -> Unit){
        Log.i("Echo/On", event)
        val listener = Emitter.Listener { (channel, data) ->
            Log.i("Echo/HasEvent", event)
            if (this.name == channel) {
                callback(data as JSONObject)
            }
        }

        this.socket.on(event, listener)
        this.bind(event, listener)
    }

    private fun configureReconnector() {
        Log.i("Echo/Configure", "Configure")
        val listener = Emitter.Listener {
            this.subscribe()
        }

        this.socket.on(Socket.EVENT_RECONNECT, listener)
        this.bind(Socket.EVENT_RECONNECT, listener)

        this.socket.on(Socket.EVENT_CONNECT){
            Log.i("Echo/On", Socket.EVENT_CONNECT)
        }.on(Socket.EVENT_DISCONNECT){
            Log.i("Echo/On", Socket.EVENT_DISCONNECT)
        }
    }

    fun bind(event: String, callback: Emitter.Listener) {
        val lel:MutableList<Emitter.Listener> = try {
            this.events[event]!!
        } catch (e:NullPointerException) {
            mutableListOf()
        }

        lel.add(callback)
    }

    fun unbind(){
        for (event in this.events.keys) {
            this.events[event]!!.forEach { emit ->
                this.socket.off(event, emit)
            }
        }
    }


}