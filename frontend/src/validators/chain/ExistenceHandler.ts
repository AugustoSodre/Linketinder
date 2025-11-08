import { BaseHandler } from './BaseHandler';
import type { ChainResult } from './BaseHandler';
import { listCandidatos, listEmpresas } from '../../storage/lists';

export class ExistenceHandler extends BaseHandler {
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
      const found = listCandidatos.find(c => ((c as any).cpf ?? '') === val);
      if (!found) return { ok: false, message: this.message ?? `${this.field} não encontrado` };
      
      context.currentUser = found;
    } else {
      const found = listEmpresas.find(e => ((e as any).cnpj ?? '') === val);
      if (!found) return { ok: false, message: this.message ?? `${this.field} não encontrado` };
      context.currentUser = found;
    }
    return { ok: true };
  }
}
