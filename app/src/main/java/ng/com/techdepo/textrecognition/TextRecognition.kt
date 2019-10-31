package ng.com.techdepo.textrecognition


import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_text_recognition.*

class TextRecognition : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognition)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            CropImage.activity().start(this)
        }

        process_button.setOnClickListener {
            startRecognizing()
        }

        if (
            capturedImage.drawable!=null
                ){
            placeHolderImage.visibility = View.GONE
            process_button.visibility = View.VISIBLE
        }else{

            placeHolderImage.visibility = View.VISIBLE
            process_button.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            when(resultCode){
                Activity.RESULT_CANCELED ->{
                    Toast.makeText(this, "Image not Selected", Toast.LENGTH_SHORT).show()
                }

                Activity.RESULT_OK->{

                    when(requestCode){

                        CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ->{
                            val result = CropImage.getActivityResult(data)
                            capturedImage.setImageURI(result.uri)
                            if (
                                capturedImage.drawable!=null
                            ){
                                placeHolderImage.visibility = View.GONE
                                process_button.visibility = View.VISIBLE
                            }else{

                                placeHolderImage.visibility = View.VISIBLE
                                process_button.visibility = View.GONE
                            }
                        }

                        CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE->{

                            if (
                                capturedImage.drawable!=null
                            ){
                                placeHolderImage.visibility = View.GONE
                                process_button.visibility = View.VISIBLE
                            }else{

                                placeHolderImage.visibility = View.VISIBLE
                                process_button.visibility = View.GONE
                            }
                            Toast.makeText(this,"Please select an Image",Toast.LENGTH_LONG).show()

                        }

                    }
                }
                }
            }catch (e:NullPointerException){
            e.printStackTrace()
        }

    }

    fun startRecognizing() {

        if (capturedImage.drawable != null) {
            progress_bar.visibility = View.VISIBLE
            process_button.isEnabled = false
            val bitmap = (capturedImage.drawable as BitmapDrawable).bitmap
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    process_button.isEnabled = true
                    processResultText(firebaseVisionText)
                }
                .addOnFailureListener {
                    process_button.isEnabled = true
                    Toast.makeText(this, "Couldn't process image", Toast.LENGTH_LONG).show()
                    progress_bar.visibility = View.GONE
                }
        } else {
            Toast.makeText(this, "Select an Image First", Toast.LENGTH_LONG).show()
        }

    }

    private fun processResultText(resultText: FirebaseVisionText) {

        val intent = Intent(this, TextResult::class.java)

        var stringBuilder = StringBuilder()

        if (resultText.textBlocks.size == 0) {
          stringBuilder.append("No text found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text
            stringBuilder.append(blockText+"\n")

        }

       intent.putExtra("process_text",stringBuilder.toString())
        startActivity(intent)
        progress_bar.visibility = View.GONE

    }
}
