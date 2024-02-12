import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Beginner's Neural Networks in KotlinDL in Kotlin Desktop
 *
 * Adapted from https://github.com/JetBrains/KotlinDL
 */

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        example1()
        // example2()
        // example3()
        // example4()
        // example5()
    }
}
