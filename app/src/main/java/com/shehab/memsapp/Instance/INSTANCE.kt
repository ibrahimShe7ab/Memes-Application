    package com.shehab.memsapp.Instance

    import com.google.gson.GsonBuilder
    import com.shehab.memsapp.Model.ApiInterface
    import com.shehab.memsapp.Utils.UTILS
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory

    object INSTANCE {

        val RetrofitINSTANCE:ApiInterface by lazy {
            Retrofit.Builder()
                .baseUrl(UTILS.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)


        }


    }