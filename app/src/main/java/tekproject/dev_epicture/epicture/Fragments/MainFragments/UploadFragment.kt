package tekproject.dev_epicture.epicture.Fragments.MainFragments

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.frag_post_layout.*
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.R

class UploadFragment: BaseFragment() {
    private val IMAGE_CAPTURE_CODE = 1001
    private val PERMISSION_CODE = 1000
    var imageUri: Uri? = null
    private var contentResolver: ContentResolver? = null

    override fun getLayout(): Int {
        return R.layout.frag_post_layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkSelfPermission(getAct().applicationContext, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_DENIED ||
            checkSelfPermission(getAct().applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED ||
            checkSelfPermission(getAct().applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED) {

            val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

            requestPermissions(permission, PERMISSION_CODE)
        } else {
            camera_btn.setOnClickListener {
                openCamera()
            }
            gallery_btn.setOnClickListener {
                openGallery()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, "Permission successfully granted. Please refresh page", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission not granted", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, IMAGE_CAPTURE_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(context, "Could not load picture", Toast.LENGTH_SHORT).show()
            return
        }
        if (imageUri != null) {
            image_view.setImageURI(imageUri)
        } else {
            image_view.setImageURI(data?.data)
        }
    }
}