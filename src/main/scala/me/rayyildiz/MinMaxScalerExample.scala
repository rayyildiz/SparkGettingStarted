package me.rayyildiz

import org.apache.spark.ml.feature.MinMaxScaler
import org.apache.spark.ml.linalg.Vectors

/**
  * Created by rayyildiz on 6/12/2017.
  */
object MinMaxScalerExample extends App with SparkSupport {

  val dataFrame = spark
    .createDataFrame(
      Seq(
        (0, Vectors.dense(1.0, 0.1, -1.0)),
        (1, Vectors.dense(2.0, 1.1, 1.0)),
        (2, Vectors.dense(3.0, 10.1, 3.0))
      ))
    .toDF("id", "features")

  val scaler = new MinMaxScaler()
    .setInputCol("features")
    .setOutputCol("scaledFeatures")

  // Compute summary statistics and generate MinMaxScalerModel
  val scalerModel = scaler.fit(dataFrame)

  // rescale each feature to range [min, max].
  val scaledData = scalerModel.transform(dataFrame)
  println(s"Features scaled to range: [${scaler.getMin}, ${scaler.getMax}]")
  scaledData.select("features", "scaledFeatures").show()
}
