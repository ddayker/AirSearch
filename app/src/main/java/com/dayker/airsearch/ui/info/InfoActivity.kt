package com.dayker.airsearch.ui.info

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.ActivityInfoBinding
import com.dayker.airsearch.model.ResponseX
import com.dayker.airsearch.utils.Constants.ICAO_KEY
import org.koin.android.ext.android.inject

class InfoActivity : AppCompatActivity(), InfoContract.View {

    private lateinit var binding: ActivityInfoBinding
    private val presenter: InfoContract.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attachView(this)

        val icao = intent.getStringExtra(ICAO_KEY)
        if (icao != null) {
            if (presenter.checkForFavoriteAndDownload(icao)) {
                binding.favoriteButton.isChecked = true
            }
        }

        with(binding) {
            backButton.setOnClickListener {
                finish()
            }
            favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    presenter.addToFavorite(initNewFlight())
                } else {
                    presenter.deleteFromFavorite(titleICAO.text.toString())
                }
            }
        }
    }

    override fun setContent(flight: ResponseX) {
        with(binding) {
            titleICAO.text = flight.flightIcao
            company.text = flight.airlineName
            status.text = flight.status
            tvCity1.text = flight.depCity
            tvAirport1.text = flight.depName
            tvTime1.text = flight.depTime
            tvRealTime1.text = flight.depActualTime
            tvCity2.text = flight.arrCity
            tvAirport2.text = flight.arrName
            tvTime2.text = flight.arrTime
            tvRealTime2.text = flight.arrActualTime
        }
    }

    override fun setContent(flight: Flight) {
        with(binding) {
            titleICAO.text = flight.icao
            company.text = flight.company
            tvCity1.text = flight.depCity
            tvAirport1.text = flight.depAirport
            tvTime1.text = flight.depTime
            tvCity2.text = flight.arrCity
            tvAirport2.text = flight.arrAirport
            tvTime2.text = flight.arrTime
        }
    }

    override fun dataIsNotAvailable() {
        with(binding) {
            company.text = "Private Jet"
            status.text = "information is not available"
        }
    }

    private fun initNewFlight(): Flight {
        with(binding) {
            return Flight(
                icao = titleICAO.text.toString(),
                company = company.text.toString(),
                depCity = tvCity1.text.toString(),
                depAirport = tvAirport1.text.toString(),
                depTime = tvTime1.text.toString(),
                arrCity = tvCity2.text.toString(),
                arrAirport = tvAirport2.text.toString(),
                arrTime = tvTime2.text.toString()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}