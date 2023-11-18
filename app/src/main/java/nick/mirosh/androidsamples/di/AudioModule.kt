package nick.mirosh.androidsamples.di

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ViewModelComponent::class)
class AudioModule {

    @RequiresApi(Build.VERSION_CODES.S)
    @Provides
    fun provideAudioRecorder(
        @ApplicationContext context: Context
    ): MediaRecorder {
        return MediaRecorder(context).apply {
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        }
    }
}