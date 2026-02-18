package org.example.project

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

data class News(
    val id: Int,
    val title: String,
    val category: String
)

class DataRepository {

    fun getNewsFlow(): Flow<News> = flow {

        val newsList = listOf(
            News(1, "Kotlin makin populer di 2026 🚀", "Tech"),
            News(2, "Jadwal UTS segera diumumkan 📚", "Campus"),
            News(3, "Cuaca cerah sepanjang hari ☀️", "Cuaca"),
            News(4, "Motor listrik terbaru diluncurkan 🏍️", "Otomotif"),
            News(5, "AI membantu dunia pendidikan 🤖", "Tech"),
            News(6, "Beasiswa 2026 resmi dibuka 🎓", "Campus")
        )

        var index = 0

        while (true) {
            emit(newsList[index])
            delay(2000)
            index = (index + 1) % newsList.size
        }
    }

    suspend fun fetchNewsDetail(news: News): String {
        return withContext(Dispatchers.Default) {

            delay(800)

            """
${news.title}

Kategori: ${news.category}
Dipublikasikan: ${getCurrentTime()}

------------------------------------------------------------

${generateArticle(news)}

------------------------------------------------------------

Reporter: News Today Editorial Team
Sumber: Simulasi Data Internal
            """.trimIndent()
        }
    }

    private fun generateArticle(news: News): String {
        return when (news.category) {

            "Tech" -> """
Industri teknologi global kembali menunjukkan pertumbuhan signifikan di tahun 2026. 
Bahasa pemrograman Kotlin kini menjadi pilihan utama dalam pengembangan aplikasi modern.

Kotlin dinilai memiliki sintaks yang lebih ringkas, aman terhadap null pointer, 
serta mendukung clean architecture dengan lebih efisien dibandingkan Java.

Banyak perusahaan startup hingga enterprise mulai mengadopsi Kotlin 
untuk meningkatkan produktivitas dan maintainability aplikasi mereka.

Tren ini diperkirakan akan terus meningkat dalam beberapa tahun mendatang.
            """.trimIndent()

            "Campus" -> """
Pihak kampus secara resmi mengumumkan bahwa jadwal Ujian Tengah Semester (UTS) 
akan segera dirilis melalui portal akademik dalam waktu dekat.

Mahasiswa diimbau untuk mulai mempersiapkan diri sejak dini 
dan memastikan seluruh administrasi akademik telah diselesaikan.

Dekan Fakultas menyampaikan bahwa pelaksanaan UTS tahun ini 
akan lebih terstruktur serta memanfaatkan sistem digital.

Dengan sistem baru ini, proses evaluasi diharapkan lebih transparan dan efisien.
            """.trimIndent()

            "Cuaca" -> """
Badan Meteorologi memprediksi kondisi cuaca hari ini akan didominasi 
oleh langit cerah berawan sejak pagi hingga siang hari.

Memasuki sore hari, terdapat kemungkinan hujan ringan di beberapa wilayah.

Masyarakat diimbau untuk tetap membawa perlengkapan tambahan 
untuk mengantisipasi perubahan cuaca yang tidak menentu.

Suhu rata-rata hari ini berkisar antara 24 hingga 31 derajat Celsius.
            """.trimIndent()

            else -> """
Industri otomotif terus berinovasi dengan menghadirkan kendaraan listrik 
yang lebih ramah lingkungan dan efisien.

Peluncuran produk terbaru ini diharapkan mampu meningkatkan 
minat masyarakat terhadap kendaraan berbasis energi terbarukan.

Produsen menyatakan bahwa teknologi baterai terbaru 
mampu meningkatkan jarak tempuh hingga 20% lebih jauh.

Langkah ini menjadi bagian dari komitmen menuju transportasi berkelanjutan.
            """.trimIndent()
        }
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}
