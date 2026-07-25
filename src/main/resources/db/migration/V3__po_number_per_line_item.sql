-- PO number moves from the invoice header to each line item (per product).
-- Existing header values are copied onto every line of that invoice so no data
-- is lost, then the header column is dropped.

ALTER TABLE invoice_line_items ADD COLUMN po_number VARCHAR(255);

UPDATE invoice_line_items li
SET po_number = i.po_number
FROM invoices i
WHERE li.invoice_id = i.id
  AND i.po_number IS NOT NULL;

ALTER TABLE invoices DROP COLUMN po_number;
