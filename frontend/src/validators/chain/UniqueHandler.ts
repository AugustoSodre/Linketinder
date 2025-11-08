import { BaseHandler } from './BaseHandler';
import type { ChainResult } from './BaseHandler';
import { listCandidatos, listEmpresas } from '../../storage/lists';

export class UniqueHandler extends BaseHandler {
  private field: string
  private scope: 'candidato' | 'empresa'
  private message?: string

  constructor(field: string, scope: 'candidato' | 'empresa', message?: string) {
    super();
    this.field = field
    this.scope = scope
    this.message = message
  }

  protected async process(context: Record<string, any>): Promise<ChainResult> {
    const val = String(context[this.field] ?? '');
    if (this.scope === 'candidato') {
      const exists = listCandidatos.some(c => ((c as any).cpf ?? '') === val);
      if (exists) return { ok: false, message: this.message ?? `${this.field} já cadastrado` };
    } else {
      const exists = listEmpresas.some(e => ((e as any).cnpj ?? '') === val);
      if (exists) return { ok: false, message: this.message ?? `${this.field} já cadastrado` };
    }
    return { ok: true };
  }
}
