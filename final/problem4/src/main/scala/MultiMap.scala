// Fix variance as needed
class MultiMap[-K,+V](entries: List[(K,V)] = List()) {
  // Implement get
  def get[T <: K](k: T): Option[V] = entries.find(p => p._1 == k) match {
    case Some(p) => Some(p._2)
    case None => None
   }

  // Fix put as needed
  def put[W>:V] (k: K, v: W): MultiMap[K, W] = new MultiMap((k, v) :: entries)
}
