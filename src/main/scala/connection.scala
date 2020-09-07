import cats.effect.{Resource, Sync}
import cats.implicits._
import org.rocksdb.RocksDB

object connection {
  def load[F[_]](options: org.rocksdb.Options, pathToDB: String)(
    implicit F: Sync[F]): Resource[F, RocksDB] = {

    def acquire: F[RocksDB] =
      F.delay(RocksDB.loadLibrary()) *>
        F.delay( RocksDB.open(options, pathToDB))

    def release: RocksDB => F[Unit] = db => F.delay(db.close())

    Resource.make(acquire)(release)

  }

}


