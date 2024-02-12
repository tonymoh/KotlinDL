import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.dataset.mnist
import java.io.File

/**
 * Loads model from Example 3
 *
 * Loads a pre-existing model configuration and weights, then trains the model on the MNIST dataset.
 * Prints the accuracy before and after training to compare the improvement.
 */

fun example5() {
    val (train, test) = mnist()

    // Load the model configuration from a JSON file
    val model = Sequential.loadModelConfiguration(File("model/my_model2/modelConfig.json"))

    model.use {
        it.compile(
            optimizer = Adam(),
            loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
            metric = Metrics.ACCURACY
        )
        // Print a summary of the model's architecture
        it.summary()
        // Optionally, print the computational graph of the model
        print(it.kGraph())

        // Load the model weights and optimizer state from a directory
        it.loadWeights(File("model/my_model2"), loadOptimizerState = true)

        // Evaluate the model's performance on the test dataset before training and print the accuracy
        val accuracyBefore = it.evaluate(dataset = test, batchSize = 100).metrics[Metrics.ACCURACY]
        println("Accuracy before training $accuracyBefore")

        // Train the model on the training dataset with a validation set for early stopping
        it.fit(
            dataset = train,
            validationRate = 0.1,
            epochs = 1,
            trainBatchSize = 1000,
            validationBatchSize = 100
        )

        // Evaluate the model's performance on the test dataset after training and print the accuracy
        val accuracyAfterTraining = it.evaluate(dataset = test, batchSize = 100).metrics[Metrics.ACCURACY]
        println("Accuracy after training with restored optimizer: $accuracyAfterTraining")
    }
}