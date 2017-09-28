package fr.enlight.spellgenerator

import android.content.Context
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 *
 */
fun writeInFileDir(context: Context, dir: String, filename: String, content: String){
    try {
        val subDir = File(context.filesDir.absolutePath + "/$dir")
        if (!subDir.exists()) subDir.mkdir()

        val file = File(subDir.absolutePath + "/$filename")
        file.createNewFile()
        val buf = BufferedWriter(FileWriter(file, true))
        buf.append(content)
        buf.flush()
        buf.close()

    } catch (exception: Exception){
        Log.e(context.javaClass.simpleName, "Error on logging debug file $dir/$filename", exception)
    }
}
