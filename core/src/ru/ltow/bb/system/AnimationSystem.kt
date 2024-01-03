package ru.ltow.bb

class AnimationSystem (
  atlas: TextureAtlas,
  creatures: JSON,
  cameraLookAt: Vector3
): EntitySystem() {
  enum Face { SE,SW,NW,NE }
  enum Action { STAND,WALK,FALL,USE,ATTACK }
  
  val ANIMATION_FRAME_DURATION = .2f
  val animations: ImmutableArray<Entity>

  override fun addedToEngine(e: Engine) {
    animations = engine.getEntitiesFor(Family.all(Animation,Face,Billboard))
    packs = creatures(creatures)
    super()
  }

  override fun update(dt: Float) {
    animations.forEach() { e ->
      val a = Mappers.animation.get(e)
      val f = Mappers.face.get(e)
      Mappers.billboard.get(e).decal.textureRegion = a.frame(face(f.vector),dt)
    }
  }

  val packs: Map<
    String,
    Map<
      Action,
      Map<
        Face,
        Animation<TextureRegion>
      >
    >
  >

  private fun getAtlasRegions(path: String): Array<TextureAtlas.AtlasRegion> {
    val regions = atlas.findRegions(path)
    if(regions == null || regions.isEmpty) throw IllegalArgumentException("couldn't find regions: $path")
    return regions
  }

  private fun creatures(c: JSON): Map<String,Map<Pair<Action,Face>,Animation<TextureRegion>>> {
    val newpack = HashMap<
      String,
      Map<
        Pair<Action,Face>,
        Animation<TextureRegion>
      >
    >()

    c.forEach() { creature ->
      if(newpack.put(creature.name,Map()) != null) throw IllegalArgumentException("duplicate name: {$creature.name}")

      val ahashmap = HashMap<
        Action,
        Map<
          Face,
          Animation<TextureRegion>
        >
      >()

      Action.values().forEach { action ->

        val fhashmap = HashMap<
          Face,
          Animation<TextureRegion>
        >()

        Face.values().forEach { face ->
          fhashmap.put(
            face,
            Animation(
              ANIMATION_FRAME_DURATION,
              getAtlasRegions("creature/$name/${action.name}/${face.name}/"),
              Animation.PlayMode.LOOP
            )
          )
        }
        ahashmap.put(action,fhashmap.toMap())
      }
      newpack.put(creature.name,ahashmap.toMap())
    }
    return newpack.toMap()
  }

  private fun face(v: Vector3) {
    val dot = cameraLookAt.dot(v)
    val crs = cameraLookAt.crs(v)
    
    return if (dot >= 0 && crs >= 0) NE
    else if (dot >= 0 && crs < 0) SE
    else if (dot < 0 && crs >= 0) NW
    else SW
  }
}