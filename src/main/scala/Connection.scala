import cats.effect.{Resource, Sync}
import cats.implicits._
import org.rocksdb.RocksDB

object Connection {
  lazy val library = RocksDB.loadLibrary()

  def load[F[_]](options: org.rocksdb.Options, pathToDB: String)(
    implicit F: Sync[F]): Resource[F, Rocks[F]] = {

    def acquire: F[Rocks[F]] =
       F.delay(library) *>
        F.delay( Rocks[F]( RocksDB.open(options, pathToDB)))

    Resource.make(acquire)(_.close)
  }


}
