package fr.mjoudar.oqee.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.mjoudar.oqee.R
import fr.mjoudar.oqee.data.network.ApiClient
import fr.mjoudar.oqee.data.network.MovieService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://testepg.r0ro.fr/"

    // Responsible for injecting a Retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val client = OkHttpClient.Builder()
        client.followRedirects(false)
        client.followSslRedirects(false)
        client.connectTimeout(2, TimeUnit.SECONDS)
        client.callTimeout(5, TimeUnit.SECONDS)

        val cf = CertificateFactory.getInstance("X.509")
        val cert = context.resources.openRawResource(R.raw.oqee_cert)
        try {
            val ca = cf.generateCertificate(cert)
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            val tmfAlgo = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgo)
            tmf.init(keyStore)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, null)

            client.sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)

        } finally {
            cert.close()
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Responsible for injecting a MovieService instance
    @Singleton
    @Provides
    fun provideModuloService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    // Responsible for injecting an ApiClient instance
    @Singleton
    @Provides
    fun provideApiClient(apiService: MovieService) = ApiClient(apiService)
}