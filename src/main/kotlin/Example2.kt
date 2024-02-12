import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.WritingMode
import org.jetbrains.kotlinx.dl.api.core.layer.core.Dense
import org.jetbrains.kotlinx.dl.api.core.layer.core.Input
import org.jetbrains.kotlinx.dl.api.core.layer.reshaping.Flatten
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
// Import the Fashion-MNIST dataset loader and Java File class for saving the model
import org.jetbrains.kotlinx.dl.dataset.fashionMnist
import java.io.File

/**
 *  Based on https://github.com/Kotlin/kotlindl/blob/master/docs/create_your_first_nn.md
 *
 *  create_your_first_nn.md
 *
 *  The tutorial uses Kotlin DL to create a neural network, train it and use the resulting model to generate predictions.
 *
 *  It also saves the model for future use with .pb and .txt files.
 */

fun example2() {
    // Load the Fashion-MNIST dataset which includes images of clothing items
    val (train, test) = fashionMnist()

    // Create a sequential model with an input layer, a flattening layer, and three dense layers
    val model = Sequential.of(
        Input(28, 28, 1),
        Flatten(),
        Dense(300),
        Dense(100),
        Dense(10)
    )

    model.use {
        it.compile(
            optimizer = Adam(),
            loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
            metric = Metrics.ACCURACY
        )

        it.summary()

        it.fit(
            dataset = train,
            epochs = 10,
            batchSize = 100
        )

        val accuracy = it.evaluate(dataset = test, batchSize = 100).metrics[Metrics.ACCURACY]
        println("Accuracy: $accuracy")

        // Save the model in .pb and .txt format in my_model folder
        it.save(File("model/my_model"), writingMode = WritingMode.OVERRIDE)
    }
}