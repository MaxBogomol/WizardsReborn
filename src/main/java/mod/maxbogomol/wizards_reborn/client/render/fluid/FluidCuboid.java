package mod.maxbogomol.wizards_reborn.client.render.fluid;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;

//class copy pasted from mantle, that's why it's so well documented
public class FluidCuboid {

    public static final Map<Direction, FluidFace> DEFAULT_FACES;
    public static final Map<Direction, FluidFace> FLOWING_DOWN_FACES;
    static {
        DEFAULT_FACES = new EnumMap<>(Direction.class);
        FLOWING_DOWN_FACES = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            DEFAULT_FACES.put(direction, FluidFace.NORMAL);
            if (direction.getAxis() == Axis.Y) {
                FLOWING_DOWN_FACES.put(direction, FluidFace.NORMAL);
            } else {
                FLOWING_DOWN_FACES.put(direction, FluidFace.DOWN);
            }
        }
    }

    /** Fluid start, scaled for block models */
    private final Vector3f from;
    /** Fluid end, scaled for block models */
    private final Vector3f to;
    /** Block faces for the fluid */
    private final Map<Direction, FluidFace> faces;

    /** Cache for scaled from */
    @Nullable
    private Vector3f fromScaled;
    /** Cache for scaled to */
    @Nullable
    private Vector3f toScaled;

    public FluidCuboid(Vector3f from, Vector3f to, Map<Direction,FluidFace> faces) {
        this.from = from;
        this.to = to;
        this.faces = faces;
    }

    public Vector3f getFrom() {
        return from;
    }

    public Vector3f getTo() {
        return to;
    }

    public Map<Direction, FluidFace> getFaces() {
        return faces;
    }

    /**
     * Checks if the fluid has the given face
     * @param face  Face to check
     * @return  True if the face is present
     */
    @Nullable
    public FluidFace getFace(Direction face) {
        return faces.get(face);
    }

    /**
     * Gets fluid from, scaled for renderer
     * @return Scaled from
     */
    public Vector3f getFromScaled() {
        if (fromScaled == null) {
            fromScaled = new Vector3f(from);
            fromScaled.mul(1 / 16f);
        }
        return fromScaled;
    }

    /**
     * Gets fluid to, scaled for renderer
     * @return Scaled from
     */
    public Vector3f getToScaled() {
        if (toScaled == null) {
            toScaled = new Vector3f(to);
            toScaled.mul(1 / 16f);
        }
        return toScaled;
    }

    /**
     * Creates a new fluid cuboid from JSON
     * @param json  JSON object
     * @return  Fluid cuboid
     */
    public static FluidCuboid fromJson(JsonObject json) {
        Vector3f from = arrayToVector(json, "from");
        Vector3f to = arrayToVector(json, "to");
        // if faces is defined, fill with specified faces
        Map<Direction, FluidFace> faces = getFaces(json);
        return new FluidCuboid(from, to, faces);
    }

    /**
     * Gets a list of fluid cuboids from the given parent
     * @param parent  Parent JSON
     * @param key     List key
     * @return  List of cuboids
     */
    public static List<FluidCuboid> listFromJson(JsonObject parent, String key) {
        JsonElement json = parent.get(key);

        // object: one cube
        if (json.isJsonObject()) {
            return Collections.singletonList(fromJson(json.getAsJsonObject()));
        }

        // array: multiple cubes
        if (json.isJsonArray()) {
            return parseList(json.getAsJsonArray(), key, FluidCuboid::fromJson);
        }

        throw new JsonSyntaxException("Invalid fluid '" + key + "', must be an array or an object");
    }

    /**
     * Gets a face set from the given json element
     * @param json  JSON parent
     * @return  Set of faces
     */
    protected static Map<Direction, FluidFace> getFaces(JsonObject json) {
        if (!json.has("faces")) {
            return DEFAULT_FACES;
        }

        Map<Direction, FluidFace> faces = new EnumMap<>(Direction.class);
        JsonObject object = GsonHelper.getAsJsonObject(json, "faces");
        for (Entry<String, JsonElement> entry : object.entrySet()) {
            // if the direction is a face, add it
            String name = entry.getKey();
            Direction dir = Direction.byName(name);
            if (dir != null) {
                JsonObject face = GsonHelper.convertToJsonObject(entry.getValue(), name);
                boolean flowing = GsonHelper.getAsBoolean(face, "flowing", false);
                int rotation = getRotation(face, "rotation");
                faces.put(dir, new FluidFace(flowing, rotation));
            } else {
                throw new JsonSyntaxException("Unknown face '" + name + "'");
            }
        }
        return faces;
    }

    /**
     * Gets a rotation from JSON
     * @param json  JSON parent
     * @return  Integer of 0, 90, 180, or 270
     */
    public static int getRotation(JsonObject json, String key) {
        int i = GsonHelper.getAsInt(json, key, 0);
        if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
            return i;
        } else {
            throw new JsonParseException("Invalid '" + key + "' " + i + " found, only 0/90/180/270 allowed");
        }
    }

    /**
     * Converts a JSON array with 3 elements into a Vector3f
     * @param json  JSON object
     * @param name  Name of the array in the object to fetch
     * @return  Vector3f of data
     * @throws JsonParseException  If there is no array or the length is wrong
     */
    public static Vector3f arrayToVector(JsonObject json, String name) {
        return arrayToObject(json, name, 3, arr -> new Vector3f(arr[0], arr[1], arr[2]));
    }

    /**
     * Converts a JSON float array to the specified object
     * @param json    JSON object
     * @param name    Name of the array in the object to fetch
     * @param size    Expected array size
     * @param mapper  Functon to map from the array to the output type
     * @param <T> Output type
     * @return  Vector3f of data
     * @throws JsonParseException  If there is no array or the length is wrong
     */
    public static <T> T arrayToObject(JsonObject json, String name, int size, Function<float[], T> mapper) {
        JsonArray array = GsonHelper.getAsJsonArray(json, name);
        if (array.size() != size) {
            throw new JsonParseException("Expected " + size + " " + name + " values, found: " + array.size());
        }
        float[] vec = new float[size];
        for(int i = 0; i < size; ++i) {
            vec[i] = GsonHelper.convertToFloat(array.get(i), name + "[" + i + "]");
        }
        return mapper.apply(vec);
    }

    /**
     * Parses a list from an JsonArray
     * @param array   Json array
     * @param name    Json key of the array
     * @param mapper  Mapper from the element object and name to new object
     * @param <T>     Output type
     * @return  List of output objects
     */
    public static <T> List<T> parseList(JsonArray array, String name, BiFunction<JsonElement,String,T> mapper) {
        if (array.size() == 0) {
            throw new JsonSyntaxException(name + " must have at least 1 element");
        }
        // build the list
        ImmutableList.Builder<T> builder = ImmutableList.builder();
        for (int i = 0; i < array.size(); i++) {
            builder.add(mapper.apply(array.get(i), name + "[" + i + "]"));
        }
        return builder.build();
    }

    /**
     * Parses a list from an JsonArray
     * @param array   Json array
     * @param name    Json key of the array
     * @param mapper  Mapper from the json object to new object
     * @param <T>     Output type
     * @return  List of output objects
     */
    public static <T> List<T> parseList(JsonArray array, String name, Function<JsonObject,T> mapper) {
        return parseList(array, name, (element, s) -> mapper.apply(GsonHelper.convertToJsonObject(element, s)));
    }

    /** Represents a single fluid face in the model */
    public record FluidFace(boolean isFlowing, int rotation) {
        public static final FluidFace NORMAL = new FluidFace(false, 0);
        public static final FluidFace DOWN = new FluidFace(true, 0);
    }
}