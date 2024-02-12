import org.jetbrains.kotlinx.dl.api.inference.TensorFlowInferenceModel
import org.jetbrains.kotlinx.dl.dataset.fashionMnist
import java.io.File

/**
 * Load model from Example 2
 *
 * need implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.3.0") for TensorFlowInferenceModel
 */

fun example4() {
    // Load the Fashion-MNIST dataset which includes images of clothing items
    val (train, test) = fashionMnist()

    // Define a mapping from numerical labels to their corresponding string descriptions
    val stringLabels = mapOf(
        0 to "T-shirt/top",
        1 to "Trousers",
        2 to "Pullover",
        3 to "Dress",
        4 to "Coat",
        5 to "Sandals",
        6 to "Shirt",
        7 to "Sneakers",
        8 to "Bag",
        9 to "Ankle boots"
    )

    // Load a pre-trained TensorFlow model from disk
    TensorFlowInferenceModel.load(File("model/my_model")).use {
        // Reshape the input tensor to match the expected shape of the model
        it.reshape(28, 28, 1)
        // Make a prediction on the first test image
        val prediction = it.predict(test.getX(0))
        // Get the actual label of the first test image
        val actualLabel = test.getY(0)

        println("Predicted label is: $prediction. This corresponds to class  ${stringLabels[prediction]}.")
        println("Actual label is: $actualLabel.")
    }

}