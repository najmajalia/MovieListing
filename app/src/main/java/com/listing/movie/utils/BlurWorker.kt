package com.listing.movie.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream

class BlurWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val appContext = applicationContext

        // Get the input image URI
        val imageUri = inputData.getString(KEY_IMAGE_URI)
        return try {
            if (imageUri != null) {
                // Decode the input image file into a Bitmap
                val inputStream = appContext.contentResolver.openInputStream(Uri.parse(imageUri))
                val inputBitmap = BitmapFactory.decodeStream(inputStream)
                // Blur the Bitmap
                val outputBitmap = blurBitmap(appContext, inputBitmap)

                // Save the output Bitmap to a file
                val outputUri = saveBitmapToFile(appContext, outputBitmap)

                // Return the output URI as the result
                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
                Result.success(outputData)
            } else {
                Result.failure()
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.failure()
        }
    }

    private fun blurBitmap(context: Context, bitmap: Bitmap): Bitmap {
        val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val renderScript = RenderScript.create(context)
        val inputAllocation = Allocation.createFromBitmap(renderScript, bitmap)
        val outputAllocation = Allocation.createFromBitmap(renderScript, outputBitmap)
        val blurScript = ScriptIntrinsicBlur.create(renderScript, inputAllocation.element)
        blurScript.setRadius(10f)
        blurScript.setInput(inputAllocation)
        blurScript.forEach(outputAllocation)
        outputAllocation.copyTo(outputBitmap)
        renderScript.destroy()
        return outputBitmap
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri {
        val filename = "blurred_image.png"
        val file = File(context.filesDir, filename)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        return Uri.fromFile(file)
    }

    companion object {
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    }
}
