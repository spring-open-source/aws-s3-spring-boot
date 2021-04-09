CREATE TABLE binary_items(
  id UUID PRIMARY KEY, 
  item_name CHARACTER VARYING (255) NOT NULL,
  deletable BOOLEAN NOT NULL,
  bucket_name CHARACTER VARYING(255) NOT NULL, 
  item_key CHARACTER VARYING(255) NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);