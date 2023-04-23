package com.example.tictactoe_pd1_mc.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.tictactoe_pd1_mc.R
import com.example.tictactoe_pd1_mc.activity.ui.theme.TicTacToe_PD1_MCTheme

class MainActivity : ComponentActivity() {

    private var player1Turn = true
    private var roundCount = 0
    private lateinit var buttons: Array<Array<Button?>>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toGameScreen = Intent(this, GameActivity::class.java)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, ("Hello Gamer!"), Toast.LENGTH_LONG).show()

        val pvpbutton = findViewById<Button>(R.id.PlayerButton)
        val aibutton = findViewById<Button>(R.id.AIPlayer)


        pvpbutton.setOnClickListener{
            setContentView(R.layout.activity_name2player)

            val gamestate = true
            val continuebutton = findViewById<Button>(R.id.Contbutton)


            toGameScreen.putExtra("gamestate", gamestate.toString())
            continuebutton.setOnClickListener{
                val name1s = findViewById<EditText>(R.id.editTextText)
                val name2s= findViewById<EditText>(R.id.editTextText2)
                toGameScreen.putExtra("name1", name1s.getText().toString())
                toGameScreen.putExtra("name2", name2s.getText().toString())
                startActivity(toGameScreen)
            }

        }
        aibutton.setOnClickListener{
            setContentView(R.layout.activity_pvename)

            val continuebutton = findViewById<Button>(R.id.contbutton)
            continuebutton.setOnClickListener {


                val name1s = findViewById<EditText>(R.id.editTextText3)
                val name2s = "Computer"

                toGameScreen.putExtra("name1", name1s.getText().toString())
                toGameScreen.putExtra("name2", name2s)


                startActivity(toGameScreen)
            }

        }




}
}



