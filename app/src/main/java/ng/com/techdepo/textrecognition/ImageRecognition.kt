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
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_image_recognition.*

class ImageRecognition : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_recognition)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab_image.setOnClickListener {
            CropImage.activity().start(this)
        }

        process_button_image.setOnClickListener {
            image_details_result.text = ""
            startRecognizing()
        }


        if (
            capturedImage_image.drawable!=null
        ){
            placeHolderImage.visibility = View.GONE
            process_button_image.visibility = View.VISIBLE
        }else{

            placeHolderImage.visibility = View.VISIBLE
            process_button_image.visibility = View.GONE
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
                            capturedImage_image.setImageURI(result.uri)
                            if (
                                capturedImage_image.drawable!=null
                            ){
                                placeHolderImage.visibility = View.GONE
                                process_button_image.visibility = View.VISIBLE
                            }else{

                                placeHolderImage.visibility = View.VISIBLE
                                process_button_image.visibility = View.GONE
                            }
                        }

                        CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE->{

                            if (
                                capturedImage_image.drawable!=null
                            ){
                                placeHolderImage.visibility = View.GONE
                                process_button_image.visibility = View.VISIBLE
                            }else{

                                placeHolderImage.visibility = View.VISIBLE
                                process_button_image.visibility = View.GONE
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

        if (capturedImage_image.drawable != null) {
            progress_bar.visibility = View.VISIBLE
            process_button_image.isEnabled = false
            val bitmap = (capturedImage_image.drawable as BitmapDrawable).bitmap
            val fireBaseImage = FirebaseVisionImage.fromBitmap(bitmap)
            val options = FirebaseVisionOnDeviceImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.7F)
                .build()
            val labelDetector = FirebaseVision.getInstance().getOnDeviceImageLabeler(options)
            labelDetector.processImage(fireBaseImage)
                .addOnSuccessListener {
                    process_button_image.isEnabled = true

                    processResultImage(it)

                   progress_bar

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Couldn't process image", Toast.LENGTH_LONG).show()
                    process_button_image.isEnabled = true
                   progress_bar
                }
        }else {
            Toast.makeText(this, "Select an Image First", Toast.LENGTH_LONG).show()
        }

    }

    private fun processResultImage(imageLabel: List<FirebaseVisionImageLabel>) {


        if (imageLabel == null) {
            image_details_result.append("Details not found")
            return
        }
        for (label in imageLabel) {

            image_details_result.append(label.text+"\n")

        }

        progress_bar.visibility = View.GONE

    }
}
