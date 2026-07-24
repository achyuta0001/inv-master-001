-- Add covering indexes for foreign keys that lacked them. Without these,
-- joins and ON DELETE/UPDATE cascade checks on the referenced tables fall back
-- to sequential scans. IF NOT EXISTS keeps this idempotent if any index was
-- created out-of-band on a managed instance.

CREATE INDEX IF NOT EXISTS idx_analytics_cache_company_id       ON analytics_cache (company_id);
CREATE INDEX IF NOT EXISTS idx_customers_company_id             ON customers (company_id);
CREATE INDEX IF NOT EXISTS idx_customers_created_by_user_id     ON customers (created_by_user_id);
CREATE INDEX IF NOT EXISTS idx_invoice_line_items_invoice_id    ON invoice_line_items (invoice_id);
CREATE INDEX IF NOT EXISTS idx_invoice_sequences_company_id     ON invoice_sequences (company_id);
CREATE INDEX IF NOT EXISTS idx_invoices_company_id              ON invoices (company_id);
CREATE INDEX IF NOT EXISTS idx_invoices_created_by              ON invoices (created_by);
CREATE INDEX IF NOT EXISTS idx_material_price_history_material_id ON material_price_history (material_id);
CREATE INDEX IF NOT EXISTS idx_materials_company_id             ON materials (company_id);
CREATE INDEX IF NOT EXISTS idx_materials_created_by_user_id     ON materials (created_by_user_id);
CREATE INDEX IF NOT EXISTS idx_payments_invoice_id             ON payments (invoice_id);
CREATE INDEX IF NOT EXISTS idx_product_materials_material_id    ON product_materials (material_id);
CREATE INDEX IF NOT EXISTS idx_product_price_history_product_id ON product_price_history (product_id);
CREATE INDEX IF NOT EXISTS idx_products_company_id              ON products (company_id);
CREATE INDEX IF NOT EXISTS idx_products_created_by_user_id      ON products (created_by_user_id);
CREATE INDEX IF NOT EXISTS idx_users_company_id                ON users (company_id);
