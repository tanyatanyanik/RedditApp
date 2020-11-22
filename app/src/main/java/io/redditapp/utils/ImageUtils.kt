package io.redditapp.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object ImageUtils {

    val IMAGES_FOLDER = "RedditPictures"

    fun saveImage(resolver: ContentResolver, bitmap: Bitmap, name: String): Boolean {
        val saved: Boolean
        val fos: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + IMAGES_FOLDER)
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = resolver.openOutputStream(imageUri!!)
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ).toString() + File.separator + IMAGES_FOLDER
            val file = File(imagesDir)
            if (!file.exists())
                file.mkdir()
            val image = File(imagesDir, name + ".jpg")
            fos = FileOutputStream(image)
        }
        saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos?.flush()
        fos?.close()
        return saved
    }


}