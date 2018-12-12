package ch.bfh.christianmueller.mymemory.onboarding


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.ImageView
import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivityActionInterface

class ProfileFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 32;
    private lateinit var callback: StartActivityActionInterface
    private lateinit var photoButton: Button
    private lateinit var mImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        callback = requireContext() as StartActivityActionInterface ?: throw (RuntimeException("shit happends"))
        photoButton = view.findViewById(R.id.bu_take_picture)
        photoButton.setOnClickListener { takeAPicture() }
        mImageView = view.findViewById(R.id.iv_profile_picture)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        callback.finishedProfileMenuClicked()
        return true
    }

    private fun takeAPicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            mImageView.setImageBitmap(rotateBitmap(imageBitmap, -90f ))
            photoButton.text = getString(R.string.bu_take_other_picture)
        }
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

}
