// Fix variance as needed
class MultiMap[K,V](entries: List[(K,V)] = List()) {
  // Implement get
  def get(k: K) = ...
  // Fix put as needed
  def put(k: K, v: V): MultiMap[K, V] = new MultiMap((k, v) :: entries)
}
