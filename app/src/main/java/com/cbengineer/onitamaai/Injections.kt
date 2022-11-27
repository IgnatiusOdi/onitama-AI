package com.cbengineer.onitamaai

object Injections {
    /**
     * Function injection for board copy
     * @author <a href="https://stackoverflow.com/users/2196460/hotkey">hotkey</a>
     */
    fun Array<Array<Piece?>>.copy() = Array(size) { get(it).clone() }
}