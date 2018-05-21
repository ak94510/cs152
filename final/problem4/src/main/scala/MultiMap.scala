// Fix variance as needed
class MultiMap[-K,+V](entries: List[(K,V)] = List()) {
  // Implement get
  def get[T <: K](k: T): List[V] = entries.find(p => p._1 == k) match {
    case Some(p) => p._2 :: Nil
    case None => List()
   }

  // Fix put as needed
  def put[W>:V] (k: K, v: W): MultiMap[K, W] = new MultiMap((k, v) :: entries)
}
