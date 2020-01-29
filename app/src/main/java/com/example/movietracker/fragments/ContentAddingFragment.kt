package com.example.movietracker.fragments

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.movietracker.utils.AppConstants
import com.example.movietracker.R
import com.example.movietracker.dao.movie.Movie
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.view_models.MovieViewModel
import com.example.movietracker.view_models.TvShowViewModel
import kotlinx.android.synthetic.main.content_selector.view.*
import kotlinx.android.synthetic.main.fragment_content_adding.view.*
import kotlinx.android.synthetic.main.movie_adding.*
import kotlinx.android.synthetic.main.movie_adding.view.*
import kotlinx.android.synthetic.main.picture_adding.*
import kotlinx.android.synthetic.main.picture_adding.view.*
import kotlinx.android.synthetic.main.tv_shows_adding.*
import kotlinx.android.synthetic.main.tv_shows_adding.view.*
import java.util.*


class ContentAddingFragment : Fragment(), View.OnClickListener {

    private var view123: View? = null
    private var imageUriCapture: Uri? = null
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var tvShowViewModel: TvShowViewModel
    private val arrayGenres =
        arrayOf("Action", "Drama", "Horror", "Comedy", "Thriller", "Western", "Romantic")

    private lateinit var imageName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view123 = inflater.inflate(R.layout.fragment_content_adding, container, false)
        setUpView(view123 as View)
        setUpSpinnerGenres(view123 as View)
        view123?.radioButtonMovies?.setOnClickListener(this)
        view123?.radioButtonTvShows?.setOnClickListener(this)
        view123?.buttonAddMovie?.setOnClickListener(this)
        view123?.buttonAddTvShow?.setOnClickListener(this)
        view123?.buttonGallery?.setOnClickListener(this)
        return view123
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.radioButtonMovies -> setUpView(view123 as View, true)
            R.id.radioButtonTvShows -> setUpView(view123 as View, false)
            R.id.buttonAddMovie -> addMovie()
            R.id.buttonAddTvShow -> addTvShow()
            R.id.buttonGallery -> pickImageFromGallery()
        }
    }

    // Results from opened intents for results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.PICK_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            imageUriCapture = data?.data
            imageViewPicture.visibility = View.VISIBLE
            imageViewPicture.setImageBitmap(imageUriCapture?.let { imageUri -> getImage(imageUri) })
        }
    }

    private fun setUpView(view: View) {
        view.radioButtonMovies.isChecked = true
        view.radioButtonTvShows.isChecked = false
        view.camlidza.addView(
            LayoutInflater.from(context).inflate(
                R.layout.movie_adding,
                view.camlidza,
                false
            )
        )
    }

    private fun setUpView(view: View, booleanMovies: Boolean) {
        if (booleanMovies) {
            view.radioButtonMovies.isChecked = true
            view.radioButtonTvShows.isChecked = false
            view.camlidza.removeAllViews()
            view.camlidza.addView(
                LayoutInflater.from(context).inflate(
                    R.layout.movie_adding,
                    view.camlidza,
                    false
                )
            )
            view.buttonAddMovie?.setOnClickListener(this)
            setUpSpinnerGenres(view)
        } else {
            view.radioButtonMovies.isChecked = false
            view.radioButtonTvShows.isChecked = true
            view.camlidza.removeAllViews()
            view.camlidza.addView(
                LayoutInflater.from(context).inflate(
                    R.layout.tv_shows_adding,
                    view.camlidza,
                    false
                )
            )
            view.buttonAddTvShow?.setOnClickListener(this)
        }
    }

    private fun setUpSpinnerGenres(view: View) {
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            arrayGenres
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        view.constraint_movie_adding.spinnerGenres.adapter = adapter
    }

    private fun addMovie() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.insertMovie(
            Movie(
                editTextMovieName.text.toString(),
                spinnerGenres.selectedItem.toString(),
                editTextDuration.text.toString().toInt(),
                editTextYear.text.toString().toInt(),
                imageUriCapture.toString()
            )
        )
        clearMoviesFields()
    }

    private fun addTvShow() {
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        tvShowViewModel.insertTvShow(
            TvShow(
                editTextTvShowsName.text.toString(),
                editTextNumberOfSeassons.text.toString().toInt(),
                editTextTvShowsYear.text.toString().toInt(),
                editTextDescription.text.toString(),
                imageUriCapture.toString(),
            0,0
            )
        )
        clearTvShowsFields()
    }

    // Pick image from gallery
    private fun pickImageFromGallery() {
        imageName = UUID.randomUUID().toString()
        startActivityForResult(
            Intent.createChooser(
                Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                "Select image"
            ), AppConstants.PICK_IMAGE_GALLERY
        )
    }

    // Getting bitmap from Uri
    @TargetApi(Build.VERSION_CODES.P)
    private fun getImage(selectedPhotoUri: Uri): Bitmap {
        val source = ImageDecoder.createSource(context!!.contentResolver, selectedPhotoUri)
        val bitmap = ImageDecoder.decodeBitmap(source)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            Matrix().apply {},
            true
        )
    }

    private fun clearMoviesFields() {
        editTextMovieName.text.clear()
        editTextDuration.text.clear()
        editTextYear.text.clear()
    }

    private fun clearTvShowsFields(){
        editTextDescription.text.clear()
        editTextNumberOfSeassons.text.clear()
        editTextTvShowsName.text.clear()
        editTextTvShowsYear.text.clear()
    }
}
