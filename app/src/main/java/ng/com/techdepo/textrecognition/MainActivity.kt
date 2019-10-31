package ng.com.techdepo.textrecognition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title="Scan! Copy! Recognize"

        text_recognition.setOnClickListener {
            startActivity(Intent(this,TextRecognition::class.java))
        }

    }




}
