package com.example.android_minesweeper.screens.best_times

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android_minesweeper.R
import com.example.android_minesweeper.models.AppDatabase
import com.example.android_minesweeper.databinding.HighScoresScreenBinding
import com.example.android_minesweeper.screens.game.GameScreenFragmentArgs
import com.example.android_minesweeper.view_models.HighScoresViewModel

class HighScoresFragment : Fragment() {

    private lateinit var binding: HighScoresScreenBinding
    private lateinit var viewModel: HighScoresViewModel
    private lateinit var viewModelFactory: HighScoresViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.show()

        binding = DataBindingUtil.inflate(inflater, R.layout.high_scores_screen, container, false)

        val defaultDifficulty = HighScoresFragmentArgs.fromBundle(arguments!!).difficulty

//        binding.difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                parent?.getItemAtPosition(position)?.toString()?.getDifficultyEnum()?.let { difficulty ->
//                    viewModel.difficultyChosenToDisplay(difficulty)
//                }
//            }
//
//        }

        val dataSource = AppDatabase.getInstance(requireNotNull(this.activity).application).highScoreDao
        viewModelFactory = HighScoresViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HighScoresViewModel::class.java)
        binding.highScoresViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}