package ng.com.techdepo.textrecognition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_text_result.*

class TextResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_result)

        supportActionBar!!.title = "Copied text"
        processed_text.setText(intent.getStringExtra("process_text"))

        share_button.setOnClickListener {
            shareText()
        }
    }


    fun shareText(){

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("process_text"))
        startActivity(Intent.createChooser(shareIntent,"Share To"))
    }
}
