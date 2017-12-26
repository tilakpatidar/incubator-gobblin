/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.gobblin.example.githubjsontoparquet;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.gobblin.writer.partitioner.WriterPartitioner;


/**
 * Partitioner for github json records based on 'PARTITION_KEY' key.
 * @author tilakpatidar
 */
public class GithubDataEventTypesPartitioner implements WriterPartitioner<GenericRecord> {

  private static final String SCHEMA_STRING = "schemaString";
  private static final Schema SCHEMA =
      SchemaBuilder.record("Schema").namespace("gobblin.writer.partitioner").fields().name(SCHEMA_STRING)
          .type(Schema.create(Schema.Type.STRING)).noDefault().endRecord();
  private static final String PARTITION_KEY = "type";

  @Override
  public Schema partitionSchema() {
    return SCHEMA;
  }

  @Override
  public GenericRecord partitionForRecord(GenericRecord record) {
    GenericRecord partition = new GenericData.Record(SCHEMA);
    partition.put(SCHEMA_STRING, record.get(PARTITION_KEY).toString().replace("\"",""));
    return partition;
  }
}

