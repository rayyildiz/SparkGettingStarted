package me.rayyildiz

import org.apache.spark.ml.feature.MaxAbsScaler
import org.apache.spark.ml.linalg.Vectors

/**
  * Created by rayyildiz on 6/12/2017.
  */
object MaxAbsScalerExample extends App with SparkSupport {

  val dataFrame = spark
    .createDataFrame(
      Seq(
        (0, Vectors.dense(1.0, 0.1, -8.0)),
        (1, Vectors.dense(2.0, 1.0, -4.0)),
        (2, Vectors.dense(4.0, 10.0, 8.0))
      ))
    .toDF("id", "features")

  val scaler = new MaxAbsScaler()
    .setInputCol("features")
    .setOutputCol("scaledFeatures")

  // Compute summary statistics and generate MaxAbsScalerModel
  val scalerModel = scaler.fit(dataFrame)

  // rescale each feature to range [-1, 1]
  val scaledData = scalerModel.transform(dataFrame)
  scaledData.select("features", "scaledFeatures").show()

}
