import cats.effect.{Resource, Sync}
import cats.implicits._
import org.rocksdb.RocksDB

object Connection {
  def load[F[_]](options: org.rocksdb.Options, pathToDB: String)(
    implicit F: Sync[F]): Resource[F, Rocks[F]] = {

    def acquire: F[Rocks[F]] =
      F.delay(RocksDB.loadLibrary()) *>
        F.delay( Rocks[F]( RocksDB.open(options, pathToDB)))

    Resource.make(acquire)(_.close)

  }


}
