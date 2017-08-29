package laravel.echo.channel

/**
 * Created by baslenvm on 19/8/2560.
 */

import laravel.echo.Options
import org.json.JSONObject

abstract class Channel {
    /**
     * The Echo options.
     *
     * @type {object}
     */
    abstract val options: Options

    /**
     * Listen for an event on the channel instance.
     *
     * @param  {string} event
     * @param  {() -> Unit} callback
     * @return {Channel}
     */
    abstract fun listen(event: String, callback: (data: JSONObject) -> Unit):Channel

    /**
     * Listen for an event on the channel instance.
     *
     * @param  {string} event
     * @param  {() -> Unit} callback
     * @return {Channel}
     */
    fun notification(callback: (data:JSONObject) -> Unit): Channel {
        return this.listen(".Illuminate\\Notifications\\Events\\BroadcastNotificationCreated", callback)
    }

    /**
     * Listen for a whisper event on the channel instance.
     *
     * @param  {string} event
     * @param  {() -> Unit}   callback
     * @return {PusherChannel}
     */
    fun listenForWhisper(event: String, callback: (data:JSONObject) -> Unit): Channel {
        return this.listen(".client-" + event, callback)
    }
 }