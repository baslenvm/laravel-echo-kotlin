package laravel.echo

import io.socket.client.IO

/**
 * Created by baslenvm on 20/8/2560.
 */

class Options {
    class Auth{
        class Headers{
            var Authorization:String =""
        }
        val headers = Headers()
    }
    var host = "0.0.0.0"
    var auth = Auth()
    var authEndpoint:String = "/broadcasting/auth"
    var broadcaster:String = "pusher"
    var csrfToken:String = ""
    var key:String = ""
    var namespace:String = "App.Events"
}


