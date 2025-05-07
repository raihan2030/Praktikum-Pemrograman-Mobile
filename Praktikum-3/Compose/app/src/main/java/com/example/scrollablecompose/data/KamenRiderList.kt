package com.example.scrollablecompose.data

import com.example.scrollablecompose.R
import com.example.scrollablecompose.model.KamenRider

fun getKamenRiderList(): List<KamenRider> {
    return listOf(
        KamenRider(
            id = 1,
            name = "Kamen Rider Geats",
            description = "Para peserta bertarung dalam Desire Grand Prix untuk menjadi pahlawan ideal di dunia seperti permainan.",
            year = 2022,
            imdbUrl = "https://www.imdb.com/title/tt20758104/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_geats
        ),
        KamenRider(
            id = 2,
            name = "Kamen Rider Build",
            description = "Seorang fisikawan jenius berubah menjadi Build untuk mengungkap kebenaran di balik Kotak Pandora.",
            year = 2017,
            imdbUrl = "https://www.imdb.com/title/tt6982472/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_build
        ),
        KamenRider(
            id = 3,
            name = "Kamen Rider Zero One",
            description = "Di dunia yang dipenuhi AI dan robot, Aruto bertarung sebagai Zero-One demi melindungi manusia dan Humagear.",
            year = 2019,
            imdbUrl = "https://www.imdb.com/title/tt10333650/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_zero_one
        ),
        KamenRider(
            id = 4,
            name = "Kamen Rider W (Double)",
            description = "Dua jiwa dalam satu tubuh, Shotaro dan Philip bekerja sama sebagai detektif untuk melindungi Futo dari kejahatan Gaia Memory.",
            year = 2009,
            imdbUrl = "https://www.imdb.com/title/tt1483620/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_double
        ),
        KamenRider(
            id = 5,
            name = "Kamen Rider Gaim",
            description = "Para Rider bertarung dalam permainan yang tampak seperti hiburan, namun berubah menjadi konflik serius demi menyelamatkan dunia.",
            year = 2013,
            imdbUrl = "https://www.imdb.com/title/tt3079058/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_gaim
        ),
        KamenRider(
            id = 6,
            name = "Kamen Rider Ex-Aid",
            description = "Seorang dokter sekaligus gamer bertarung melawan virus digital Bugster demi menyelamatkan pasien dari penyakit misterius.",
            year = 2016,
            imdbUrl = "https://www.imdb.com/title/tt5813014/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_ex_aid
        ),
        KamenRider(
            id = 7,
            name = "Kamen Rider Gotchard",
            year = 2023,
            description = "Rinne dan Houtarou bertarung bersama menggunakan kartu Chemies untuk menjaga keseimbangan dunia.",
            imdbUrl = "https://www.imdb.com/title/tt27830205/?ref_=ext_shr_lnk",
            posterRes = R.drawable.kr_gotchard
        )
    )
}