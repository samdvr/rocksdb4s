import cats.effect.Sync
import org.rocksdb.RocksDB
import fs2._
trait Rocks[F[_]] {
  def get(key: Array[Byte]): Stream[F,Array[Byte]]

  def put(key: Array[Byte], value: Array[Byte]): F[Unit]

  def delete(key: Array[Byte]): F[Unit]

  def close: F[Unit]
}

object Rocks {
  def apply[F[_]](db: RocksDB)(implicit F: Sync[F]) : Rocks[F] = new Rocks[F] {
    override def get(key: Array[Byte]): Stream[F, Array[Byte]] =
      Stream.eval(F.delay(db.get(key)))

    override def put(key: Array[Byte], value: Array[Byte]): F[Unit] =
      F.delay(db.put(key,value))

    override def delete(key: Array[Byte]): F[Unit] =
      F.delay(db.delete(key))

    override def close: F[Unit] =
      F.delay(db.close())
  }
}
