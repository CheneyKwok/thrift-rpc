/**
 * Autogenerated by Thrift Compiler (0.20.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package github.cheneykwok.thrift.gen;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.20.0)", date = "2024-07-22")
public class Request implements org.apache.thrift.TBase<Request, Request._Fields>, java.io.Serializable, Cloneable, Comparable<Request> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Request");

  private static final org.apache.thrift.protocol.TField METHOD_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("methodName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PARAMETERS_FIELD_DESC = new org.apache.thrift.protocol.TField("parameters", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField PARAMETER_TYPES_FIELD_DESC = new org.apache.thrift.protocol.TField("parameterTypes", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new RequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new RequestTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String methodName; // required
  public @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> parameters; // optional
  public @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> parameterTypes; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    METHOD_NAME((short)1, "methodName"),
    PARAMETERS((short)2, "parameters"),
    PARAMETER_TYPES((short)3, "parameterTypes");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // METHOD_NAME
          return METHOD_NAME;
        case 2: // PARAMETERS
          return PARAMETERS;
        case 3: // PARAMETER_TYPES
          return PARAMETER_TYPES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    @Override
    public short getThriftFieldId() {
      return _thriftId;
    }

    @Override
    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.PARAMETERS,_Fields.PARAMETER_TYPES};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.METHOD_NAME, new org.apache.thrift.meta_data.FieldMetaData("methodName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAMETERS, new org.apache.thrift.meta_data.FieldMetaData("parameters", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.PARAMETER_TYPES, new org.apache.thrift.meta_data.FieldMetaData("parameterTypes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Request.class, metaDataMap);
  }

  public Request() {
  }

  public Request(
    java.lang.String methodName)
  {
    this();
    this.methodName = methodName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Request(Request other) {
    if (other.isSetMethodName()) {
      this.methodName = other.methodName;
    }
    if (other.isSetParameters()) {
      java.util.List<java.lang.String> __this__parameters = new java.util.ArrayList<java.lang.String>(other.parameters);
      this.parameters = __this__parameters;
    }
    if (other.isSetParameterTypes()) {
      java.util.List<java.lang.String> __this__parameterTypes = new java.util.ArrayList<java.lang.String>(other.parameterTypes);
      this.parameterTypes = __this__parameterTypes;
    }
  }

  @Override
  public Request deepCopy() {
    return new Request(this);
  }

  @Override
  public void clear() {
    this.methodName = null;
    this.parameters = null;
    this.parameterTypes = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getMethodName() {
    return this.methodName;
  }

  public Request setMethodName(@org.apache.thrift.annotation.Nullable java.lang.String methodName) {
    this.methodName = methodName;
    return this;
  }

  public void unsetMethodName() {
    this.methodName = null;
  }

  /** Returns true if field methodName is set (has been assigned a value) and false otherwise */
  public boolean isSetMethodName() {
    return this.methodName != null;
  }

  public void setMethodNameIsSet(boolean value) {
    if (!value) {
      this.methodName = null;
    }
  }

  public int getParametersSize() {
    return (this.parameters == null) ? 0 : this.parameters.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getParametersIterator() {
    return (this.parameters == null) ? null : this.parameters.iterator();
  }

  public void addToParameters(java.lang.String elem) {
    if (this.parameters == null) {
      this.parameters = new java.util.ArrayList<java.lang.String>();
    }
    this.parameters.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getParameters() {
    return this.parameters;
  }

  public Request setParameters(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public void unsetParameters() {
    this.parameters = null;
  }

  /** Returns true if field parameters is set (has been assigned a value) and false otherwise */
  public boolean isSetParameters() {
    return this.parameters != null;
  }

  public void setParametersIsSet(boolean value) {
    if (!value) {
      this.parameters = null;
    }
  }

  public int getParameterTypesSize() {
    return (this.parameterTypes == null) ? 0 : this.parameterTypes.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getParameterTypesIterator() {
    return (this.parameterTypes == null) ? null : this.parameterTypes.iterator();
  }

  public void addToParameterTypes(java.lang.String elem) {
    if (this.parameterTypes == null) {
      this.parameterTypes = new java.util.ArrayList<java.lang.String>();
    }
    this.parameterTypes.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getParameterTypes() {
    return this.parameterTypes;
  }

  public Request setParameterTypes(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> parameterTypes) {
    this.parameterTypes = parameterTypes;
    return this;
  }

  public void unsetParameterTypes() {
    this.parameterTypes = null;
  }

  /** Returns true if field parameterTypes is set (has been assigned a value) and false otherwise */
  public boolean isSetParameterTypes() {
    return this.parameterTypes != null;
  }

  public void setParameterTypesIsSet(boolean value) {
    if (!value) {
      this.parameterTypes = null;
    }
  }

  @Override
  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case METHOD_NAME:
      if (value == null) {
        unsetMethodName();
      } else {
        setMethodName((java.lang.String)value);
      }
      break;

    case PARAMETERS:
      if (value == null) {
        unsetParameters();
      } else {
        setParameters((java.util.List<java.lang.String>)value);
      }
      break;

    case PARAMETER_TYPES:
      if (value == null) {
        unsetParameterTypes();
      } else {
        setParameterTypes((java.util.List<java.lang.String>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case METHOD_NAME:
      return getMethodName();

    case PARAMETERS:
      return getParameters();

    case PARAMETER_TYPES:
      return getParameterTypes();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case METHOD_NAME:
      return isSetMethodName();
    case PARAMETERS:
      return isSetParameters();
    case PARAMETER_TYPES:
      return isSetParameterTypes();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof Request)
      return this.equals((Request)that);
    return false;
  }

  public boolean equals(Request that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_methodName = true && this.isSetMethodName();
    boolean that_present_methodName = true && that.isSetMethodName();
    if (this_present_methodName || that_present_methodName) {
      if (!(this_present_methodName && that_present_methodName))
        return false;
      if (!this.methodName.equals(that.methodName))
        return false;
    }

    boolean this_present_parameters = true && this.isSetParameters();
    boolean that_present_parameters = true && that.isSetParameters();
    if (this_present_parameters || that_present_parameters) {
      if (!(this_present_parameters && that_present_parameters))
        return false;
      if (!this.parameters.equals(that.parameters))
        return false;
    }

    boolean this_present_parameterTypes = true && this.isSetParameterTypes();
    boolean that_present_parameterTypes = true && that.isSetParameterTypes();
    if (this_present_parameterTypes || that_present_parameterTypes) {
      if (!(this_present_parameterTypes && that_present_parameterTypes))
        return false;
      if (!this.parameterTypes.equals(that.parameterTypes))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMethodName()) ? 131071 : 524287);
    if (isSetMethodName())
      hashCode = hashCode * 8191 + methodName.hashCode();

    hashCode = hashCode * 8191 + ((isSetParameters()) ? 131071 : 524287);
    if (isSetParameters())
      hashCode = hashCode * 8191 + parameters.hashCode();

    hashCode = hashCode * 8191 + ((isSetParameterTypes()) ? 131071 : 524287);
    if (isSetParameterTypes())
      hashCode = hashCode * 8191 + parameterTypes.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Request other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetMethodName(), other.isSetMethodName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMethodName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.methodName, other.methodName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetParameters(), other.isSetParameters());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParameters()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parameters, other.parameters);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetParameterTypes(), other.isSetParameterTypes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParameterTypes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parameterTypes, other.parameterTypes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  @Override
  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  @Override
  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Request(");
    boolean first = true;

    sb.append("methodName:");
    if (this.methodName == null) {
      sb.append("null");
    } else {
      sb.append(this.methodName);
    }
    first = false;
    if (isSetParameters()) {
      if (!first) sb.append(", ");
      sb.append("parameters:");
      if (this.parameters == null) {
        sb.append("null");
      } else {
        sb.append(this.parameters);
      }
      first = false;
    }
    if (isSetParameterTypes()) {
      if (!first) sb.append(", ");
      sb.append("parameterTypes:");
      if (this.parameterTypes == null) {
        sb.append("null");
      } else {
        sb.append(this.parameterTypes);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (methodName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'methodName' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public RequestStandardScheme getScheme() {
      return new RequestStandardScheme();
    }
  }

  private static class RequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<Request> {

    @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, Request struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // METHOD_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.methodName = iprot.readString();
              struct.setMethodNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARAMETERS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.parameters = new java.util.ArrayList<java.lang.String>(_list0.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.parameters.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setParametersIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PARAMETER_TYPES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list3 = iprot.readListBegin();
                struct.parameterTypes = new java.util.ArrayList<java.lang.String>(_list3.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem4;
                for (int _i5 = 0; _i5 < _list3.size; ++_i5)
                {
                  _elem4 = iprot.readString();
                  struct.parameterTypes.add(_elem4);
                }
                iprot.readListEnd();
              }
              struct.setParameterTypesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    @Override
    public void write(org.apache.thrift.protocol.TProtocol oprot, Request struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.methodName != null) {
        oprot.writeFieldBegin(METHOD_NAME_FIELD_DESC);
        oprot.writeString(struct.methodName);
        oprot.writeFieldEnd();
      }
      if (struct.parameters != null) {
        if (struct.isSetParameters()) {
          oprot.writeFieldBegin(PARAMETERS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.parameters.size()));
            for (java.lang.String _iter6 : struct.parameters)
            {
              oprot.writeString(_iter6);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.parameterTypes != null) {
        if (struct.isSetParameterTypes()) {
          oprot.writeFieldBegin(PARAMETER_TYPES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.parameterTypes.size()));
            for (java.lang.String _iter7 : struct.parameterTypes)
            {
              oprot.writeString(_iter7);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public RequestTupleScheme getScheme() {
      return new RequestTupleScheme();
    }
  }

  private static class RequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<Request> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Request struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.methodName);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetParameters()) {
        optionals.set(0);
      }
      if (struct.isSetParameterTypes()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetParameters()) {
        {
          oprot.writeI32(struct.parameters.size());
          for (java.lang.String _iter8 : struct.parameters)
          {
            oprot.writeString(_iter8);
          }
        }
      }
      if (struct.isSetParameterTypes()) {
        {
          oprot.writeI32(struct.parameterTypes.size());
          for (java.lang.String _iter9 : struct.parameterTypes)
          {
            oprot.writeString(_iter9);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Request struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.methodName = iprot.readString();
      struct.setMethodNameIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list10 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.parameters = new java.util.ArrayList<java.lang.String>(_list10.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _elem11;
          for (int _i12 = 0; _i12 < _list10.size; ++_i12)
          {
            _elem11 = iprot.readString();
            struct.parameters.add(_elem11);
          }
        }
        struct.setParametersIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list13 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.parameterTypes = new java.util.ArrayList<java.lang.String>(_list13.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = iprot.readString();
            struct.parameterTypes.add(_elem14);
          }
        }
        struct.setParameterTypesIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

