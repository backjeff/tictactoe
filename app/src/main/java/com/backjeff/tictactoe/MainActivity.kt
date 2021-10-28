package com.backjeff.tictactoe

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.backjeff.tictactoe.databinding.ActivityMainBinding
import com.backjeff.tictactoe.model.GameMode

class MainActivity : WearableActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enables Always-on
        setAmbientEnabled()

        setupViews()
    }

    private fun setupViews() {
        binding.buttonVsAi.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, GameActivity::class.java).apply {
                    putExtra("GAME_MODE", "HUMAN_VS_AI")
                }
            )
        }

        binding.buttonVsPlayer.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, GameActivity::class.java).apply {
                    putExtra("GAME_MODE", "HUMAN_VS_HUMAN")
                }
            )
        }
    }
}
