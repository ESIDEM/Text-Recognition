package ng.com.techdepo.textrecongnition

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_text_recognition.*
import kotlinx.android.synthetic.main.dialog_layout.*

class TextRecognition : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognition)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            CropImage.activity().start(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            val result = CropImage.getActivityResult(data)
            capturedImage.setImageURI(result.uri)
        }
        else if (
            resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE
        ){
            Toast.makeText(this,"Please select an Image",Toast.LENGTH_LONG).show()
        }
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            capturedImage.setImageURI(data!!.data)
//
//        }
    }

    fun showDialogue(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)

        dialog.show()

    }

    fun startRecognizing(v: View) {
        progress_bar.visibility = View.VISIBLE
        if (capturedImage.drawable != null) {
            v.isEnabled = false
            val bitmap = (capturedImage.drawable as BitmapDrawable).bitmap
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    v.isEnabled = true
                    processResultText(firebaseVisionText)
                }
                .addOnFailureListener {
                    v.isEnabled = true
                  //  processed_text.setText("Failed")
                }
        } else {
            Toast.makeText(this, "Select an Image First", Toast.LENGTH_LONG).show()
        }

    }

    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
           // processed_text.setText( "No Text Found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text
          //  processed_text.append(blockText + "\n")
        }

        progress_bar.visibility = View.GONE
        showDialogue()
    }
}
