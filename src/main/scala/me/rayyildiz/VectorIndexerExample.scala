package me.rayyildiz

import org.apache.spark.ml.feature.VectorIndexer

/**
  * Created by rayyildiz on 6/12/2017.
  */
object VectorIndexerExample extends App with SparkSupport {

  val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")

  val indexer = new VectorIndexer()
    .setInputCol("features")
    .setOutputCol("indexed")
    .setMaxCategories(10)

  val indexerModel = indexer.fit(data)

  val categoricalFeatures: Set[Int] = indexerModel.categoryMaps.keys.toSet
  println(
    s"Chose ${categoricalFeatures.size} categorical features: " +
      categoricalFeatures.mkString(", "))

  // Create new column "indexed" with categorical values transformed to indices
  val indexedData = indexerModel.transform(data)
  indexedData.show()
}
